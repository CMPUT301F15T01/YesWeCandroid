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
 * Taken from AndroidElasticSearch //TODO: add github link
 */


public class Hits<T> {
    private int total;
    private float max_score;
    private List<SearchHit<T>> hits;

    public Hits() {
    }
/*
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public float getMax_score() {
        return max_score;
    }

    public void setMax_score(float max_score) {
        this.max_score = max_score;
    }
*/
    public List<SearchHit<T>> getHits() {
        return hits;
    }
/*
    public void setHits(List<SearchHit<T>> hits) {
        this.hits = hits;
    }
*/

    /**
     * Returns the number of hits (results) that a search query returns from elastic search
     *
     * @return Integer - number of Hits (results) returned
     */
    public Integer numHits() {
        return hits.size();
    }

    @Override
    public String toString() {
        return "Hits [total=" + total + ", max_score=" + max_score + ", hits="
                + hits + "]";
    }
}
