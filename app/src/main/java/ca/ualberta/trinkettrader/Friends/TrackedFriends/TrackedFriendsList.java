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

package ca.ualberta.trinkettrader.Friends.TrackedFriends;

import ca.ualberta.trinkettrader.Friends.FriendsList;

/**
 * Subclass of current user's {@link FriendsList FriendsList} that contains subset of Friends that the
 * current user is tracking.  When the current user chooses to track a friend in the
 * {@link ca.ualberta.trinkettrader.Friends.FriendsProfileActivity FriendsProfileActivity} then that
 * {@link ca.ualberta.trinkettrader.Friends.Friend Friend} is added to the current user's TrackedFriendsList.
 * Note that the tracked friend will now be in both the user's FriendsList and trackedFriendsList.  The
 * current user can also remove friends from their TrackedFriendsList by setting that friend's tracked status
 * to "untracked" in the friend's FriendsProfileActivity, in the event of which that friend will be removed
 * from the TrackedFriendsList but will remain in the FriendsList.  Deleting the friend altogether (also done
 * in he FriendsProfileActivity) will remove the friend from both the FriendsList and TrackedFriendsList.
 *
 * This class has exactly the same attributes and method implementations as FriendsList, and exists in
 * order to better organise a user's friends as tracked and untracked.
 */

public class TrackedFriendsList extends FriendsList {
}
