package com.sprintpay.projetsig.service;

import com.sprintpay.projetsig.dto.PrincipalStationCriteria;
import com.sprintpay.projetsig.repository.PrincipalStationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import com.sprintpay.projetsig.model.PrincipalStation;
import com.sprintpay.projetsig.model.PrincipalStation_;

@Service
@Transactional(readOnly = true)
public class PrincipalStationQueryService extends QueryService<PrincipalStation> {


    private final PrincipalStationRepository principalStationRepository;

    public PrincipalStationQueryService(PrincipalStationRepository principalStationRepository) {
        this.principalStationRepository = principalStationRepository;
    }

    @Transactional(readOnly = true)
    public Page<PrincipalStation> findByCriteria(PrincipalStationCriteria principalStationCriteria, Pageable pageable){
        final Specification<PrincipalStation> specification = createSpecification(principalStationCriteria);

        return principalStationRepository.findAll(specification, pageable);
    }

    private Specification<PrincipalStation> createSpecification(PrincipalStationCriteria principalStationCriteria) {
        Specification<PrincipalStation> specification = Specification.where(null);
        if (principalStationCriteria != null){
            if (principalStationCriteria.getId() != null){
                specification = specification.and(buildRangeSpecification(principalStationCriteria.getId(),PrincipalStation_.id));
            }

            if (principalStationCriteria.getType() != null){
                specification = specification.and(buildSpecification(principalStationCriteria.getType(),PrincipalStation_.type));
            }


            if (principalStationCriteria.getFieldName() != null){
                specification = specification.and(buildStringSpecification(principalStationCriteria.getFieldName(),PrincipalStation_.fieldName));
            }

        }
        return specification;
    }
}
