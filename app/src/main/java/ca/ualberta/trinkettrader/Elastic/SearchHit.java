// Copyright 2015 Andrea McIntosh, Dylan Ashley, Anju Eappen, Jenna Hatchard, Kirsten Svidal, Raghav Vamaraju
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package ca.ualberta.trinkettrader.Elastic;

public class SearchHit<T> {
    private String _index;
    private String _type;
    private String _id;
    private String _version;
    private boolean found;
    private T _source;

    public SearchHit() {

    }

    /**
     * Returns whether the SearchHit was found. This method can be used to determine if a particular
     * data type was found on the server.
     * @return boolean
     */
    public boolean isFound() {
        return found;
    }

    /**
     * Sets whether the SearchHit object was found.
     * @param found
     */
    public void setFound(boolean found) {
        this.found = found;
    }

    /**
     * Gets the query intended object of type T that was stored on the server. This is an object that
     * was instantiated by the system, stored and now retrieved. This object can be edited and restored
     * to the server for future queries.
     * @return T
     */
    public T getSource() {
        return _source;
    }
    /**
     * Sets the query intended object of type T that was stored on the server. This is an object that
     * was instantiated by the system, stored and now set. This object can be edited and restored
     * to the server for future queries. The JSON string that is parsed into this object must match
     * the object's attributes.
     * @return T
     */
    public void setSource(T source) {
        this._source = source;
    }

    /**
     * Return the Elastic Search response with each attribute of the SearchHit in a readable format.
     * @return String
     */
    @Override
    public String toString() {
        return "SimpleElasticSearchResponse [_index=" + _index + ", _type="
                + _type + ", _id=" + _id + ", _version=" + _version
                + ", found=" + found + ", _source=" + _source + "]";
    }
}
