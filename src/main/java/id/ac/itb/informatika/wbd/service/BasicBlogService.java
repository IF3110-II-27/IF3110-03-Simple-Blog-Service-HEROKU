package id.ac.itb.informatika.wbd.service;

import id.ac.itb.informatika.wbd.model.Comment;
import id.ac.itb.informatika.wbd.model.Post;
import id.ac.itb.informatika.wbd.model.User;
import java.util.ArrayList;
import javax.jws.WebService;

@WebService
public interface BasicBlogService {

        Boolean addPost(String judul, String konten, String tanggal);
        
        ArrayList<Post> listPost();
        
        Boolean editPost(String id, String judul, String konten, String tanggal);
        
        Boolean deletePost(String id);
        
        Boolean publishPost(String id);
        
        Boolean addUser(String nama, String email, String password, String role);
        
        ArrayList<User> listUser();
  
        Boolean editUser(String id, String nama, String email, String password, String role);
 
        Boolean deleteUser(String id);
        
        Boolean addComment(String postId, String nama, String email, String konten, String tanggal);

        ArrayList<Comment> listComment(String postId);
        
        ArrayList<Post> search(String query);
        
        Boolean restorePost(String id);
        
        Boolean deletePostPermanent(String id);

}
