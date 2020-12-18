package me.dibro.gallery.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Meme {

    @Id
    @GeneratedValue
    private UUID id;

    private String imageUri;

    @ManyToOne
    @JoinColumn
    private User author;

    @ManyToMany
    private List<User> likedBy;
}
