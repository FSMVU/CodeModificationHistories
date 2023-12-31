


amizurov
on Tue Jul 26 2022

amizurov
on Mon Dec 07 2020

benjaminroux
on Tue Nov 03 2020
Revert "Add support for heartbeat in STOMP decoder/encoder. (#10695)" (#10766) ...

benjaminroux
on Fri Oct 30 2020

artem-smotrakov
on Fri Oct 23 2020

amizurov
on Tue Mar 31 2020

amizurov
on Wed Nov 06 2019

fenik17
on Fri Jun 23 2017

Scottmitch
on Thu Aug 13 2015

buchgr
on Wed Apr 15 2015

Scottmitch
on Fri Sep 19 2014

trustin
on Thu Jun 05 2014

trustin
on Wed Jun 04 2014
/*
 * Copyright 2014 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.util.List;
import java.util.Map.Entry;


/**
 * Encodes a {@link StompFrame} or a {@link StompSubframe} into a {@link ByteBuf}.
 */
public class StompSubframeEncoder extends MessageToMessageEncoder<StompSubframe> {

    @Override
    protected void encode(ChannelHandlerContext ctx, StompSubframe msg, List<Object> out) throws Exception {
        if (msg instanceof StompFrame) {

            StompFrame frame = (StompFrame) msg;
            ByteBuf frameBuf = encodeFrame(frame, ctx);
            if (frame.content().isReadable()) {
                out.add(frameBuf);
                ByteBuf contentBuf = encodeContent(frame, ctx);
                out.add(contentBuf);
            } else {
                frameBuf.writeByte(StompConstants.NUL);
                out.add(frameBuf);
            }
        } else if (msg instanceof StompHeadersSubframe) {

            StompHeadersSubframe frame = (StompHeadersSubframe) msg;
            ByteBuf buf = encodeFrame(frame, ctx);
            out.add(buf);
        } else if (msg instanceof StompContentSubframe) {
            StompContentSubframe stompContentSubframe = (StompContentSubframe) msg;
            ByteBuf buf = encodeContent(stompContentSubframe, ctx);

            out.add(buf);
        }
    }




    private static ByteBuf encodeContent(StompContentSubframe content, ChannelHandlerContext ctx) {
        if (content instanceof LastStompContentSubframe) {
            ByteBuf buf = ctx.alloc().buffer(content.content().readableBytes() + 1);
            buf.writeBytes(content.content());
            buf.writeByte(StompConstants.NUL);
            return buf;
        } else {
            return content.content().retain();
        }

    }

    private static ByteBuf encodeFrame(StompHeadersSubframe frame, ChannelHandlerContext ctx) {
        ByteBuf buf = ctx.alloc().buffer();



        buf.writeCharSequence(frame.command().toString(), CharsetUtil.UTF_8);
        buf.writeByte(StompConstants.LF);

        for (Entry<CharSequence, CharSequence> entry : frame.headers()) {
            ByteBufUtil.writeUtf8(buf, entry.getKey());
            buf.writeByte(StompConstants.COLON);
            ByteBufUtil.writeUtf8(buf, entry.getValue());
            buf.writeByte(StompConstants.LF);
        }

        buf.writeByte(StompConstants.LF);
        return buf;
    }


}