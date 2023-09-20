    public String getName() {
        return name;
    }

    /**
     * Version of Elasticsearch that the node is running or {@code null}
     * if we don't know the version.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Roles that the Elasticsearch process on the host has or {@code null}
     * if we don't know what roles the node has.
     */
    public Roles getRoles() {
        return roles;
    }


    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("[host=").append(host);
        if (boundHosts != null) {
            b.append(", bound=").append(boundHosts);
        }
        if (name != null) {
            b.append(", name=").append(name);
        }
        if (version != null) {
            b.append(", version=").append(version);
        }
        if (roles != null) {
            b.append(", roles=").append(roles);
        }
        return b.append(']').toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        Node other = (Node) obj;
        return host.equals(other.host)
            && Objects.equals(boundHosts, other.boundHosts)
            && Objects.equals(name, other.name)
            && Objects.equals(version, other.version)
            && Objects.equals(roles, other.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, boundHosts, name, version, roles);
    }

    /**
     * Role information about an Elasticsearch process.
     */
    public static final class Roles {
        private final boolean masterEligible;
        private final boolean data;
        private final boolean ingest;

        public Roles(boolean masterEligible, boolean data, boolean ingest) {
            this.masterEligible = masterEligible;
            this.data = data;
            this.ingest = ingest;
        }

        /**
         * Teturns whether or not the node <strong>could</strong> be elected master.
         */
        public boolean isMasterEligible() {
            return masterEligible;
        }
        /**
         * Teturns whether or not the node stores data.
         */
        public boolean isData() {
            return data;
        }
        /**
         * Teturns whether or not the node runs ingest pipelines.
         */
        public boolean isIngest() {
            return ingest;
        }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder(3);
            if (masterEligible) result.append('m');
            if (data) result.append('d');
            if (ingest) result.append('i');
            return result.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || obj.getClass() != getClass()) {
                return false;
            }
            Roles other = (Roles) obj;
            return masterEligible == other.masterEligible
                && data == other.data
                && ingest == other.ingest;
        }

        @Override
        public int hashCode() {
            return Objects.hash(masterEligible, data, ingest);
        }
    }
}