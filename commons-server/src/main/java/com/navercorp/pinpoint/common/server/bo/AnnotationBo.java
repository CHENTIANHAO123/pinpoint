/*
 * Copyright 2014 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.navercorp.pinpoint.common.server.bo;

import com.navercorp.pinpoint.common.buffer.Buffer;
import com.navercorp.pinpoint.common.util.AnnotationTranscoder;

/**
 * @author emeroad
 */
public class AnnotationBo {

    @Deprecated
    private static final AnnotationTranscoder transcoder = new AnnotationTranscoder();

    private byte version = 0;
    private long spanId;

    private int key;

    private byte valueType;
    private byte[] byteValue;
    private Object value;
    private boolean isAuthorized = true;
    
    public AnnotationBo() {
    }

    @Deprecated
    public long getSpanId() {
        return spanId;
    }

    @Deprecated
    public void setSpanId(long spanId) {
        this.spanId = spanId;
    }

    public int getVersion() {
        return version & 0xFF;
    }

    public byte getRawVersion() {
        return version;
    }

    public void setVersion(int version) {
        if (version < 0 || version > 255) {
            throw new IllegalArgumentException("out of range (0~255) " + version);
        }
        // check range
        this.version = (byte) (version & 0xFF);
    }

    public int getKey() {
        return key;
    }


    public void setKey(int key) {
        this.key = key;
    }


    public int getValueType() {
        return valueType;
    }

    public byte getRawValueType() {
        return valueType;
    }

    public void setValueType(byte valueType) {
        this.valueType = valueType;
    }

    public byte[] getByteValue() {
        return byteValue;
    }

    public void setByteValue(byte[] byteValue) {
        this.byteValue = byteValue;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }


    @Deprecated
    public void writeValue(Buffer buffer) {
        // long timestamp;    // required 8
        // long duration;     // optional 8
        // int key;           // required 4
        // int valueTypeCode; // required 4
        // ByteBuffer value;  // optional 4 + buf.length
        buffer.putByte(this.version);
        buffer.putSVInt(this.key);
        buffer.putByte(this.valueType);
        buffer.putPrefixedBytes(this.byteValue);
    }

    @Deprecated
    public void readValue(Buffer buffer) {
        this.version = buffer.readByte();
        this.key = buffer.readSVInt();
        this.valueType = buffer.readByte();
        this.byteValue = buffer.readPrefixedBytes();
        this.value = transcoder.decode(valueType, byteValue);
    }

    @Override
    public String toString() {
        if (value == null) {
            return "AnnotationBo{" + "version=" + version + ", spanId=" + spanId + ", key='" + key + '\'' + ", valueType=" + valueType + '}';
        }
        return "AnnotationBo{" + "version=" + version + ", spanId=" + spanId + ", key='" + key + '\'' + ", value=" + value + '}';
    }

}
