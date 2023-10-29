package pl.dk.usermanager.domain.user;

class UpdateUserNotPossibleException extends RuntimeException {
    public UpdateUserNotPossibleException(String message) {
        super(message);
    }
}
