package id.ac.itb.informatika.wbd.service;

import com.firebase.client.Firebase;
import id.ac.itb.informatika.wbd.model.Comment;
import id.ac.itb.informatika.wbd.model.Post;
import id.ac.itb.informatika.wbd.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.ws.rs.client.ClientBuilder;
import org.json.JSONObject;

public class BasicBlogServiceImpl implements BasicBlogService {

    @Override
    public Boolean addPost(String judul, String konten, String tanggal) {
        Post newPost = new Post();
        newPost.setTitle(judul);
        newPost.setDate(tanggal);
        newPost.setContent(konten);
        newPost.setPublished("false");
        newPost.setDeleted("false");
        
        Firebase fb = new Firebase("https://if3110-iii-27.firebaseio.com/");
        fb.child("posts").push().setValue(newPost);
        
        return true;
    }

    @Override
    public ArrayList<Post> listPost() {
        ArrayList<Post> posts = new ArrayList<>();
        
        String response = ClientBuilder.newClient()
                .target("https://if3110-iii-27.firebaseio.com/")
                .path("posts.json?orderByChild=\"date\"")
                .request()
                .get(String.class);

        try {
            JSONObject json = new JSONObject(response);
            Iterator<String> keys = json.keys();

            while (keys.hasNext()) {
                String key = keys.next();
                JSONObject val = json.getJSONObject(key);

                Post post = new Post();
                post.setId(key);
                post.setTitle(val.getString("title"));
                post.setContent(val.getString("content"));
                post.setPublished(val.getString("published"));
                post.setDate(val.getString("date"));
                post.setDeleted(val.getString("deleted"));

                posts.add(post);
            }
        } catch (Exception ex) {
            System.err.printf(ex.getMessage());
        }
        
        return posts;
    }

    @Override
    public Boolean editPost(String id, String judul, String konten, String tanggal) {
        Firebase fb = new Firebase("https://if3110-iii-27.firebaseio.com/");
        Map<String, Object> val = new HashMap<>();
        val.put("title", judul);
        val.put("date", tanggal);
        val.put("content", konten);
        fb.child("posts").child(id).updateChildren(val);
        
        return true;
    }

    @Override
    public Boolean deletePost(String id) {
        Firebase fb = new Firebase("https://if3110-iii-27.firebaseio.com/");
        Map<String, Object> val = new HashMap<>();
        val.put("deleted", "true");
        fb.child("posts").child(id).updateChildren(val);
        
        return true;
    }

    @Override
    public Boolean publishPost(String id) {
        Firebase fb = new Firebase("https://if3110-iii-27.firebaseio.com/");
        Map<String, Object> val = new HashMap<>();
        val.put("published", "true");
        fb.child("posts").child(id).updateChildren(val);
        
        return true;
    }

    @Override
    public Boolean addUser(String nama, String email, String password, String role) {
        User newUser = new User();
        newUser.setName(nama);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRole(role);
        
        Firebase fb = new Firebase("https://if3110-iii-27.firebaseio.com/");
        fb.child("users").push().setValue(newUser);
        
        return true;
    }

    @Override
    public ArrayList<User> listUser() {
        ArrayList<User> users = new ArrayList<>();
       
        String response = ClientBuilder.newClient()
                .target("https://if3110-iii-27.firebaseio.com/")
                .path("users.json")
                .request()
                .get(String.class);

        JSONObject json = new JSONObject(response);
        Iterator<String> keys = json.keys();

        while (keys.hasNext()) {
            String key = keys.next();
            JSONObject val = json.getJSONObject(key);
            User user = new User();
            user.setId(key);
            user.setEmail(val.getString("email"));
            user.setName(val.getString("name"));
            user.setPassword(val.getString("password"));
            user.setRole(val.getString("role"));
            
            users.add(user);
        }
        
        return users;
    }

    @Override
    public Boolean editUser(String id, String nama, String email, String password, String role) {
        Firebase fb = new Firebase("https://if3110-iii-27.firebaseio.com/");
        Map<String, Object> val = new HashMap<>();
        val.put("name", nama);
        val.put("email", email);
        val.put("password", password);
        val.put("role", role);
        fb.child("users").child(id).updateChildren(val);
        
        return true;
        
    }

    @Override
    public Boolean deleteUser(String id) {
        Firebase fb = new Firebase("https://if3110-iii-27.firebaseio.com/");
        fb.child("users").child(id).removeValue();
        
        return true;
    }

    @Override
    public Boolean addComment(String postId, String nama, String email, String konten, String tanggal) {
        Comment newComment = new Comment();
        newComment.setPostId(postId);
        newComment.setName(nama);
        newComment.setContent(konten);
        newComment.setDate(tanggal);
        
        Firebase fb = new Firebase("https://if3110-iii-27.firebaseio.com/");
        fb.child("comments").push().setValue(newComment);
        
        return true;
    }

    @Override
    public ArrayList<Comment> listComment(String postId) {
        Comment comment;
        ArrayList<Comment> comments = new ArrayList<>();
        
        String response = ClientBuilder.newClient()
                .target("https://if3110-iii-27.firebaseio.com/")
                .path("comments.json")
                .request()
                .get(String.class);

        try {
            JSONObject json = new JSONObject(response);
            Iterator<String> keys = json.keys();

            while (keys.hasNext()) {
                String key = keys.next();
                JSONObject val = json.getJSONObject(key);

                comment = new Comment();
                comment.setId(key);
                comment.setName(val.getString("name"));
                comment.setContent(val.getString("content"));
                comment.setDate(val.getString("date"));
                comment.setPostId(val.getString("postId"));

                if (postId.equals(comment.getPostId())) {
                    comments.add(comment);
                }
            }
        } catch (Exception ex) {
            System.err.printf(ex.getMessage());
        }
        
        return comments;
    }

    @Override
    public ArrayList<Post> search(String query) {
        return listPost();
    }

    @Override
    public Boolean restorePost(String id) {
        Firebase fb = new Firebase("https://if3110-iii-27.firebaseio.com/");
        Map<String, Object> val = new HashMap<>();
        val.put("deleted", "false");
        fb.child("posts").child(id).updateChildren(val);
        
        return true;
    }

    @Override
    public Boolean deletePostPermanent(String id) {
        Firebase fb = new Firebase("https://if3110-iii-27.firebaseio.com/");
        fb.child("posts").child(id).removeValue();
        
        return true;
    }

}
