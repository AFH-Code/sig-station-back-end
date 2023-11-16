package com.sprintpay.projetsig.dto;

import com.sprintpay.projetsig.model.TypeStationP;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

public class PrincipalStationCriteria implements Serializable, Criteria {


    private IntegerFilter id;
    private TypeStationPFilter type;

    private StringFilter fieldName;

    @Override
    public Criteria copy() {
        return new PrincipalStationCriteria(this);
    }

    public PrincipalStationCriteria(PrincipalStationCriteria other) {
        this.id = other.id==null?null:other.id.copy();
        this.type = other.type==null?null:other.type.copy();
        this.fieldName = other.fieldName==null?null:other.fieldName.copy();

    }

    public IntegerFilter getId() {
        return id;
    }

    public TypeStationPFilter getType() {
        return type;
    }

    public StringFilter getFieldName() {
        return fieldName;
    }

    public void setId(IntegerFilter id) {
        this.id = id;
    }

    public void setType(TypeStationPFilter type) {
        this.type = type;
    }

    public void setFieldName(StringFilter fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrincipalStationCriteria that = (PrincipalStationCriteria) o;
        return id.equals(that.id) && type.equals(that.type)&& fieldName.equals(that.fieldName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, fieldName);
    }

    public PrincipalStationCriteria() {
    }

    public static  class TypeStationPFilter extends Filter<TypeStationP>{

        public TypeStationPFilter() {
        }

        public TypeStationPFilter(TypeStationPFilter filter) {
            super(filter);
        }
        @Override
        public TypeStationPFilter copy(){
            return new TypeStationPFilter(this);
        }
    }
}



