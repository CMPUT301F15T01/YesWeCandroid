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

package ca.ualberta.trinkettrader;

/**
 * Created by vamaraju on 11/5/2015.
 */
public class ApplicationState {

    private static ApplicationState ourInstance = new ApplicationState();
    private Trinket clickedTrinket;
    private Friend clickedFriend;

    private ApplicationState() {

    }

    public static ApplicationState getInstance() {
        return ourInstance;
    }

    public Friend getClickedFriend() {
        return this.clickedFriend;
    }

    public void setClickedFriend(Friend clickedFriend) {
        this.clickedFriend = clickedFriend;
    }

    public Trinket getClickedTrinket() {
        return this.clickedTrinket;
    }

    public void setClickedTrinket(Trinket clickedTrinket) {
        this.clickedTrinket = clickedTrinket;
    }
}
