


amizurov
on Tue Jul 26 2022

amizurov
on Mon Dec 07 2020

benjaminroux
on Tue Nov 03 2020

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
Overall refactoring of the STOMP codec ...
/*
 * Copyright 2014 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * Encodes a {@link StompFrame} or a {@link StompSubframe} into a {@link ByteBuf}.
 */
public class StompSubframeEncoder extends MessageToMessageEncoder<StompSubframe> {

    @Override
    protected void encode(ChannelHandlerContext ctx, StompSubframe msg, List<Object> out) throws Exception {
        if (msg instanceof StompFrame) {
            StompFrame frame = (StompFrame) msg;
            ByteBuf frameBuf = encodeFrame(frame, ctx);
            out.add(frameBuf);
            ByteBuf contentBuf = encodeContent(frame, ctx);
            out.add(contentBuf);
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

        buf.writeBytes(frame.command().toString().getBytes(CharsetUtil.US_ASCII));
        buf.writeByte(StompConstants.CR).writeByte(StompConstants.LF);

        StompHeaders headers = frame.headers();
        for (String k: headers.keySet()) {
            List<String> values = headers.getAll(k);
            for (String v: values) {
                buf.writeBytes(k.getBytes(CharsetUtil.US_ASCII)).
                        writeByte(StompConstants.COLON).writeBytes(v.getBytes(CharsetUtil.US_ASCII));
                buf.writeByte(StompConstants.CR).writeByte(StompConstants.LF);
            }
        }
        buf.writeByte(StompConstants.CR).writeByte(StompConstants.LF);
        return buf;
    }
}