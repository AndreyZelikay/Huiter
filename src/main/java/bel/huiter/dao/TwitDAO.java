package bel.huiter.dao;

import bel.huiter.forms.TwitSearchForm;
import bel.huiter.models.Twit;

import java.util.List;
import java.util.Optional;

public interface TwitDAO extends Crud<Twit> {
    List<Twit> findTwits(TwitSearchForm form);
    Optional<Twit> findById(long id);
}
