package pl.dk.usermanager.domain.user;

import lombok.Getter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import pl.dk.usermanager.domain.user.dto.UpdateUserDto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Getter
public class CustomInMemoryUserRepository implements UserRepository {
    private List<User> userList = new ArrayList<>();
    private Long userId = 1L;

    @Override
    public <S extends User> S save(S entity) {
        User userToSave = User.builder()
                .id(userId)
                .email(entity.getEmail())
                .password(entity.getPassword())
                .build();
        userList.add(userToSave);
        userId++;
        return (S) userToSave;
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        Iterator<S> iterator = entities.iterator();
        while (iterator.hasNext()) {
            userList.add(iterator.next());
            userId++;
        }
        return (List<S>) userList;
    }

    @Override
    public void flush() {
    }

    @Override
    public <S extends User> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends User> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<User> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public User getOne(Long aLong) {
        return null;
    }

    @Override
    public User getById(Long aLong) {
        return null;
    }

    @Override
    public User getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends User> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends User, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }


    @Override
    public Optional<User> findById(Long aLong) {
        return userList.stream().filter(u -> u.getId()
                .equals(aLong))
                .findFirst();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public List<User> findAllById(Iterable<Long> longs) {
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
    public void delete(User entity) {
        userList.remove(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<User> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userList.stream()
                .filter(x -> x.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public void activeUser(Long userId) {
        User user = userList.stream().filter(u -> u.getId().equals(userId)).findFirst().orElseThrow();
        userList.remove(user);
    }

    @Override
    public void updateUser(UpdateUserDto updateUserDto, String currentUser) {
        User user = userList.stream()
                .filter(u -> u.getEmail().equals(currentUser))
                .findFirst()
                .orElseThrow();

        List<User> list = userList.stream()
                .filter(u -> !u.getEmail().equals(currentUser))
                .toList();

        User userToUpdate = User.builder()
                .id(user.getId())
                .email(updateUserDto.newEmail())
                .password(updateUserDto.newPassword())
                .active(true)
                .build();

        userList.clear();
        userList.add(userToUpdate);
        userList.addAll(list);
    }


}
