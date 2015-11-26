package ca.ualberta.trinkettrader.User.Profile;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ca.ualberta.trinkettrader.Friends.Friend;
import ca.ualberta.trinkettrader.Friends.FriendsList;
import ca.ualberta.trinkettrader.Friends.TrackedFriends.TrackedFriendsList;
import ca.ualberta.trinkettrader.Inventory.Inventory;
import ca.ualberta.trinkettrader.Inventory.Trinket.Pictures.Picture;
import ca.ualberta.trinkettrader.Inventory.Trinket.Trinket;
import ca.ualberta.trinkettrader.User.User;

public class UserAdapter extends TypeAdapter<User> {

    // ALBERT ATTARD; http://www.javacreed.com/gson-typeadapter-example/; 2015-11-26

    @Override
    public void write(JsonWriter out, User value) throws IOException {
        out.beginObject();

        //Serialize FriendsList
        out.name("friendsList").beginArray();
        for (final Friend friend : value.getFriendsList()) {
            out.beginObject();
            out.name("username").value(friend.toString());
            out.name("email").value(friend.getActualFriend().getProfile().getEmail());
            out.endObject();
        }
        out.endArray();

        //Serialize Inventory
        out.name("inventory").beginArray();
        for (final Trinket trinket : value.getInventory()) {
            out.beginObject();

            out.name("pictures").beginArray();
            for (final Picture picture : trinket.getPictures()) {
                out.value(picture.getId());
            }
            out.endArray();

            out.name("accessibility").value(trinket.getTrinketAccessibility());
            out.name("category").value(trinket.getCategory());
            out.name("description").value(trinket.getDescription());
            out.name("name").value(trinket.getName());
            out.name("quality").value(trinket.getQuality());
            out.name("quantity").value(trinket.getQuantity());

            out.endObject();
        }
        out.endArray();


        //TODO: make each tracked friend an object
        //Serialize TrackedFriendsList
        out.name("trackedFriendsList").beginArray();
        for (final Friend friend : value.getTrackedFriendsList()) {

            /*out.value(friend.toString());
            out.value(friend.getActualFriend().getProfile().getEmail());*/
            out.value("trackedFriends Not implemented");
        }
        out.endArray();

        //Serialize profile
        out.name("profile").beginObject();
        out.name("arePhotosDownloadable").value(value.getProfile().getArePhotosDownloadable());

        out.name("contactInfo").beginObject();
        ContactInfo c = value.getProfile().getContactInfo();
        out.name("name").value(c.getName());
        out.name("address").value(c.getAddress());
        out.name("city").value(c.getCity());
        out.name("postalCode").value(c.getPostalCode());
        out.name("phoneNumber").value(c.getPhoneNumber());
        out.endObject();

        out.name("email").value(value.getProfile().getEmail());
        out.name("username").value(value.getProfile().getUsername());
        out.endObject();

        out.endObject();
    }

    @Override
    public User read(JsonReader in) throws IOException {
        User r = new User();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "friendsList":
                    in.beginArray();
                    //CHECK THIS BELOW
                    final List friends = new FriendsList();
                    while (in.hasNext()) {
                        in.beginObject();
                        Friend f = new Friend();
                        while (in.hasNext()) {
                            switch (in.nextName()) {
                                case "username":
                                    f.getActualFriend().getProfile().setUsername(in.nextString());
                                    continue;
                                case "email":
                                    f.getActualFriend().getProfile().setEmail(in.nextString());
                                    continue;
                                    //TODO: do a search by email on the Friends user object in elastic search and setActualFriend()
                            }
                        }
                        in.endObject();
                        friends.add(f);
                    }
                    r.setFriendsList((FriendsList) friends);
                    in.endArray();
                    continue;
                case "inventory":
                    in.beginArray();
                    final List inventory = new Inventory();
                    while (in.hasNext()) {
                        in.beginObject();
                        Trinket t = new Trinket();
                        switch (in.nextName()) {
                            case "accessibility":
                                t.setAccessibility(in.nextString());
                                continue;
                            case "category":
                                t.setCategory(in.nextString());
                                continue;
                            case "description":
                                t.setDescription(in.nextString());
                                continue;
                            case "name":
                                t.setName(in.nextString());
                                continue;
                            case "quality":
                                t.setQuality(in.nextString());
                                continue;
                            case "quantity":
                                t.setQuantity(in.nextString());
                                continue;
                            case "pictures":
                                final List pictures = new ArrayList<Picture>();
                                //TODO: Search elasticURL/pictures for each picture by ID and add here
                        }
                        inventory.add(t);
                        in.endObject();
                    }
                    r.setInventory((Inventory) inventory);
                    in.endArray();
                    continue;
                case "trackedFriendsList":
                    in.beginArray();
                    final List tracked = new TrackedFriendsList();
                    //TODO: implement tracked friendsList
                    r.setTrackedFriends((TrackedFriendsList) tracked);
                    in.endArray();
                    continue;
                case "profile":
                    in.beginObject();
                    UserProfile p = new UserProfile();
                    switch (in.nextName()) {
                        case "arePhotosDownloadable":
                            p.setArePhotosDownloadable(in.nextBoolean());
                        case "contactInfo":
                            //TODO: finish this functionality
                            p.setContactInfo(new ContactInfo());
                            continue;
                        case "email":
                            p.setEmail(in.nextString());
                            continue;
                        case "username":
                            p.setUsername(in.nextString());
                            continue;
                    }
                    r.setProfile(p);
                    in.endObject();
                    continue;
            }
        }
        return r;
    }

}
