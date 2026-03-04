package workshop.zepcla.specifications;

import org.springframework.data.jpa.domain.Specification;
import workshop.zepcla.entities.AppointmentEntity;

public class AppointmentSpecification {

    public static Specification<AppointmentEntity> hasId(Long id) {
        return ((root, query, criteriaBuilder) -> id == null ? null
                : criteriaBuilder.equal(root.get("id"), id));
    }

    public static Specification<AppointmentEntity> hasDate(String date) {
        return ((root, query, criteriaBuilder) -> date == null ? null
                : criteriaBuilder.like(root.get("date"), date));
    }

    public static Specification<AppointmentEntity> hasTime(String time) {
        return ((root, query, criteriaBuilder) -> time == null ? null
                : criteriaBuilder.like(root.get("time"), time));
    }

    public static Specification<AppointmentEntity> hasDuration(String duration) {
        return ((root, query, criteriaBuilder) -> duration == null ? null
                : criteriaBuilder.like(root.get("duration"), duration));
    }

    public static Specification<AppointmentEntity> hasEmailClient(String email_client) {
		return ((root, query, criteriaBuilder) -> email_client == null ? null
				: criteriaBuilder.like(root.get("email_client"), email_client));
	}

    public static Specification<AppointmentEntity> hasIdClient(Long id_client) {
		return ((root, query, criteriaBuilder) -> id_client == null ? null
				: criteriaBuilder.equal(root.get("id_client"), id_client));
	}
    public static Specification<AppointmentEntity> hasIdCreator(Long id_creator) {
		return ((root, query, criteriaBuilder) -> id_creator == null ? null
				: criteriaBuilder.equal(root.get("id_creator"), id_creator));
	}

    public static Specification<AppointmentEntity> hasStatus(String status) {
        return ((root, query, criteriaBuilder) -> status == null ? null
                : criteriaBuilder.like(root.get("status"), status));
    }

    public static Specification<AppointmentEntity> hasToken(String token) {
        return ((root, query, criteriaBuilder) -> token == null ? null
                : criteriaBuilder.like(root.get("token"), token));
    }

}
