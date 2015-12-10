/*
 * Copyright 2015 Couchbase, Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.couchbase.mock.subdoc;

/**
 * Opcodes for sub-document commands
 */
public enum Operation {
    GET(0xC5), EXISTS(0xC6), DICT_ADD(0xC7), DICT_UPSERT(0xC8), REMOVE(0xC9), REPLACE(0xCA),
    ARRAY_PREPEND(0xCB), ARRAY_APPEND(0xCC), ARRAY_INSERT(0xCD), ADD_UNIQUE(0xCE), COUNTER(0xCD);

    private final int value;

    Operation(int value) {
        this.value = value;
    }

    public int code() {
        return value;
    }

    public boolean requiresValue() {
        switch (this) {
            case GET:
            case EXISTS:
            case REMOVE:
                return false;
            default:
                return true;
        }
    }

    public boolean allowsMultiValue() {
        switch (this) {
            case ARRAY_APPEND:
            case ARRAY_PREPEND:
            case ARRAY_INSERT:
                return true;
            default:
                return false;
        }
    }

    public boolean isMutator() {
        switch (this) {
            case GET:
            case EXISTS:
                return false;
            default:
                return true;
        }
    }

    public boolean returnsMatch() {
        switch (this) {
            case GET:
            case COUNTER:
                return true;
            default:
                return false;
        }
    }

    // Whether value should be evaluated in an array context.
    public boolean isArrayParent() {
        switch (this) {
            case ARRAY_INSERT:
            case ARRAY_PREPEND:
            case ARRAY_APPEND:
            case ADD_UNIQUE:
                return true;
            default:
                return false;
        }
    }
}
