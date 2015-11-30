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

/**
 * Class representing results of searches to Elastic Search
 * Taken from AndroidElasticSearch on github
 */
//joshua2ua; https://github.com/joshua2ua/AndroidElasticSearch; 2015-11-30
public class SearchResponse<T> {

    private int took;
    private boolean timed_out;
    private Shard _shards;
    private Hits<T> hits;

    public SearchResponse() {
    }

    /**
     * Returns the {@link ca.ualberta.trinkettrader.Elastic.Hits Hits} returned from a search query
     * to the Elastic Search Server. Hits are parsed to get the data objects that were queried for.
     *
     * @return Hits<T> - results of a search
     */
    public Hits<T> getHits() {
        return hits;
    }


    class Shard {
        private int total;
        private int successful;
        private int failed;

        public Shard() {
        }
    }
}
