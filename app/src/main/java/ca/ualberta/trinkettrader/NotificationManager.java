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

import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;

import ca.ualberta.trinkettrader.User.User;

/**
 * Represents a manager that transmits notifications to the user.
 * Two types of notifications can be sent to a user:
 * 1. new trade offer notification - user will be notified when another user sends them a
 * new trade offer,
 * 2. completion of trade notification - user will be emailed relevant trade information
 * for a completed trade.
 */
public class NotificationManager {

    private ArrayList<Notification> pendingNotifications = new ArrayList<Notification>();
    private Integer displayNotificationCountOnHomeScreen;
    private User user;

    /**
     * Returns if this user has a notification waiting to be read.
     *
     * @return Boolean
     */
    public Boolean hasNotification() {
        return Boolean.TRUE;
    }

    /**
     * Displays notifications to the user and removes them from pending notifications.
     */
    public void notifyUser() {

    }

    /**
     * Sends email containing relevant trade information to borrower
     * and an owner after a trade has been accepted.
     */
    public void sendEmail() {
    }
}
