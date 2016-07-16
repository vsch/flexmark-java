/*
 * Copyright (c) 2015-2016 Vladimir Schneider <vladimir.schneider@gmail.com>, all rights reserved.
 *
 * This code is private property of the copyright holder and cannot be used without
 * having obtained a license or prior written permission of the of the copyright holder.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package com.vladsch.flexmark.internal.util.ast;

import com.vladsch.flexmark.internal.util.KeepType;
import com.vladsch.flexmark.internal.util.options.DataHolder;
import com.vladsch.flexmark.internal.util.options.DataKey;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class NodeRepository<T> implements Map<String, T> {
    final private Map<String, T> nodeMap = new HashMap<>();
    final private KeepType keepType;

    public abstract DataKey<? extends NodeRepository<T>> getDataKey();
    public abstract DataKey<KeepType> getKeepDataKey();

    public NodeRepository(DataHolder options) {
        this.keepType = options == null ? KeepType.LOCKED : options.get(getKeepDataKey());
    }

    public String normalizeKey(CharSequence key) {
        return key.toString();
    }

    public T getFromRaw(CharSequence rawKey) {
        return nodeMap.get(normalizeKey(rawKey));
    }

    public T putRawKey(CharSequence key, T t) {
        return put(normalizeKey(key), t); 
    }

    @Override
    public T put(String s, T t) {
        if (keepType == KeepType.LOCKED) throw new IllegalStateException("Not allowed to modify LOCKED repository");
        if (keepType != KeepType.LAST) {
            T another = nodeMap.get(s);
            if (another != null) {
                if (keepType == KeepType.FAIL) throw new IllegalStateException("Duplicate key " + s);
                return another;
            }
        }
        return nodeMap.put(s, t);
    }

    @Override
    public void putAll(Map<? extends String, ? extends T> map) {
        if (keepType == KeepType.LOCKED) throw new IllegalStateException("Not allowed to modify LOCKED repository");
        if (keepType != KeepType.LAST) {
            for (String key : map.keySet()) {
                nodeMap.put(key, map.get(key));
            }
        } else {
            nodeMap.putAll(map);
        }
    }

    @Override
    public T remove(Object o) {
        if (keepType == KeepType.LOCKED) throw new IllegalStateException("Not allowed to modify LOCKED repository");
        return nodeMap.remove(o);
    }

    @Override
    public void clear() {
        if (keepType == KeepType.LOCKED) throw new IllegalStateException("Not allowed to modify LOCKED repository");
        nodeMap.clear();
    }

    @Override
    public int size() {return nodeMap.size();}

    @Override
    public boolean isEmpty() {return nodeMap.isEmpty();}

    @Override
    public boolean containsKey(Object o) {return nodeMap.containsKey(o);}

    @Override
    public boolean containsValue(Object o) {return nodeMap.containsValue(o);}

    @Override
    public T get(Object o) {return nodeMap.get(o);}

    @Override
    public Set<String> keySet() {return nodeMap.keySet();}

    @Override
    public Collection<T> values() {return nodeMap.values();}

    @Override
    public Set<Entry<String, T>> entrySet() {return nodeMap.entrySet();}

    @Override
    public boolean equals(Object o) {return nodeMap.equals(o);}

    @Override
    public int hashCode() {return nodeMap.hashCode();}
}
