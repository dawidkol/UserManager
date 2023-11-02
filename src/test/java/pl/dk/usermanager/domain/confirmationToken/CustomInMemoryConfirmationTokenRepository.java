package pl.dk.usermanager.domain.confirmationToken;

import lombok.Getter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
@Getter
class CustomInMemoryConfirmationTokenRepository implements ConfirmationTokenRepository {

    private List<ConfirmationToken> tokens = new ArrayList<>();

    private Long tokenId = 1L;

    @Override
    public <S extends ConfirmationToken> S save(S entity) {
        ConfirmationToken tokenToSave = ConfirmationToken.builder()
                .id(tokenId)
                .token(entity.getToken())
                .user(entity.getUser())
                .build();
        tokens.add(tokenToSave);
        tokenId++;
        return (S) tokenToSave;
    }

    @Override
    public Optional<ConfirmationToken> findByToken(String token) {
        return tokens.stream()
                .filter(t -> t.getToken().equals(token))
                .findFirst();
    }
    @Override
    public void flush() {

    }

    @Override
    public <S extends ConfirmationToken> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends ConfirmationToken> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<ConfirmationToken> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public ConfirmationToken getOne(Long aLong) {
        return null;
    }

    @Override
    public ConfirmationToken getById(Long aLong) {
        return null;
    }

    @Override
    public ConfirmationToken getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends ConfirmationToken> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends ConfirmationToken> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends ConfirmationToken> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends ConfirmationToken> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends ConfirmationToken> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends ConfirmationToken> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends ConfirmationToken, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends ConfirmationToken> List<S> saveAll(Iterable<S> entities) {
        Iterator<S> iterator = entities.iterator();
        while (iterator.hasNext()) {
            tokens.add(iterator.next());
            tokenId++;
        }
        return (List<S>) tokens;
    }

    @Override
    public Optional<ConfirmationToken> findById(Long aLong) {
        return tokens.stream().filter(t -> t.getId().equals(aLong)).findFirst();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<ConfirmationToken> findAll() {
        return null;
    }

    @Override
    public List<ConfirmationToken> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(ConfirmationToken entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends ConfirmationToken> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<ConfirmationToken> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<ConfirmationToken> findAll(Pageable pageable) {
        return null;
    }
}
