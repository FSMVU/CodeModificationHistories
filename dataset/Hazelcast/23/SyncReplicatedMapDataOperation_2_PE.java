//made storage update atomic in anti-entropy and cleaned-up record removals

/*
 * Copyright (c) 2008-2015, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.replicatedmap.impl.operation;

import com.hazelcast.logging.ILogger;
import com.hazelcast.logging.Logger;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.replicatedmap.impl.ReplicatedMapService;
import com.hazelcast.replicatedmap.impl.record.AbstractReplicatedRecordStore;
import com.hazelcast.replicatedmap.impl.record.InternalReplicatedMapStorage;
import com.hazelcast.replicatedmap.impl.record.RecordMigrationInfo;
import com.hazelcast.replicatedmap.impl.record.ReplicatedRecord;
import com.hazelcast.spi.AbstractOperation;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Carries set of replicated map records for a partition from one node to another
 */
public class SyncReplicatedMapDataOperation<K, V> extends AbstractOperation {

    private static ILogger logger = Logger.getLogger(SyncReplicatedMapDataOperation.class.getName());

    private String name;
    private Set<RecordMigrationInfo> recordSet;
    private long version;

    public SyncReplicatedMapDataOperation() {
    }

    public SyncReplicatedMapDataOperation(String name, Set<RecordMigrationInfo> recordSet, long version) {
        this.name = name;
        this.recordSet = recordSet;
        this.version = version;
    }

    @Override
    public void run() throws Exception {
        logger.finest("Carrying " + recordSet.size() + " records for partition -> " + getPartitionId()
                + " from -> " + getCallerAddress() + ", to -> " + getNodeEngine().getThisAddress());
        ReplicatedMapService service = getService();
        AbstractReplicatedRecordStore store = (AbstractReplicatedRecordStore) service
                .getReplicatedRecordStore(name, true, getPartitionId());
        InternalReplicatedMapStorage<K, V> newStorage = new InternalReplicatedMapStorage<K, V>();
        for (RecordMigrationInfo record : recordSet) {
            K key = (K) store.marshall(record.getKey());
            V value = (V) store.marshall(record.getValue());
            newStorage.putInternal(key, buildReplicatedRecord(key, value, record.getTtl()));
        }
        newStorage.setVersion(version);
        AtomicReference<InternalReplicatedMapStorage<K, V>> storageRef = store.getStorageRef();
        storageRef.set(newStorage);
        store.setLoaded(true);
    }

    private ReplicatedRecord<K, V> buildReplicatedRecord(K key, V value, long ttlMillis) {
        int partitionId = getNodeEngine().getPartitionService().getPartitionId(key);
        return new ReplicatedRecord<K, V>(key, value, ttlMillis, partitionId);
    }

    @Override
    protected void writeInternal(ObjectDataOutput out) throws IOException {
        out.writeUTF(name);
        out.writeLong(version);
        out.writeInt(recordSet.size());
        for (RecordMigrationInfo record : recordSet) {
            record.writeData(out);
        }
    }

    @Override
    protected void readInternal(ObjectDataInput in) throws IOException {
        name = in.readUTF();
        version = in.readLong();
        int size = in.readInt();
        recordSet = new HashSet<RecordMigrationInfo>(size);
        for (int j = 0; j < size; j++) {
            RecordMigrationInfo record = new RecordMigrationInfo();
            record.readData(in);
            recordSet.add(record);
        }
    }
}
