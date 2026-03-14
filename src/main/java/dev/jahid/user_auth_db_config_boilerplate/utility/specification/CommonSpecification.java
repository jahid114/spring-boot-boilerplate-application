package dev.jahid.user_auth_db_config_boilerplate.utility.specification;

import org.springframework.data.jpa.domain.Specification;

public class CommonSpecification<T> {

    public Specification<T> filterByBoolean( String fieldName, Boolean value ) {
        if ( value == null )
            return Specification.where( null );

        return ( root, query, cb ) -> cb.equal( root.get( fieldName ), value );
    }

    public Specification<T> isNull( String fieldName ) {
        return ( root, query, cb ) -> cb.isNull( root.get( fieldName ) );
    }

    public Specification<T> isFalse( String fieldName ) {
        return ( root, query, cb ) -> cb.isFalse( root.get( fieldName ) );
    }

    public Specification<T> isTrue( String fieldName ) {
        return ( root, query, cb ) -> cb.isTrue( root.get( fieldName ) );
    }
}
