package workshop.zepcla.dto.userDto;

public record UserCreationFromPublicDto(String email, String password, String firstName, String lastName,
        String phoneNumber, String tokenRdv, Long enterpriseId) {
}
