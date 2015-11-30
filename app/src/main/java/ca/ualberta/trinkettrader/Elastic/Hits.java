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

import java.util.List;

/**
 * Taken from AndroidElasticSearch 
 */


public class Hits<T> {
    //joshua2ua; https://github.com/joshua2ua/AndroidElasticSearch; 2015-11-30
    private int total;
    private float max_score;
    private List<SearchHit<T>> hits;

    public Hits() {
    }

    /**
     * Returns the number of hits (results) that a search query returns from elastic search
     *
     * @return Integer - number of Hits (results) returned
     */
    public Integer numHits() {
        return hits.size();
    }

    /**
     * Returns a list of {@link ca.ualberta.trinkettrader.Elastic.SearchHit SearchHit} objects that
     * contain the data object that the system intended to store on the server, as well as some search
     * metric data. This method is used to access the data objects for processing by the system.
     * @return List<SearchHit<T>>
     */
    public List<SearchHit<T>> getHits() {
        return hits;
    }

    @Override
    public String toString() {
        return "Hits [total=" + total + ", max_score=" + max_score + ", hits="
                + hits + "]";
    }
}
