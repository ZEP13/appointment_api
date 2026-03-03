package workshop.zepcla.dto.userDto;

//ajoute un dto pour cree un admin diff d'un clien normal
public record UserCreationDto(String email, String password, String firstName, String lastName,
        String phoneNumber, String tokenRdv, Long enterpriseId) {
}
