/*******************************************************************************
 * Panifex platform
 * Copyright (C) 2013  Mario Krizmanic
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 ******************************************************************************/
package org.panifex.persistence.security;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A repository of {@link RoleEntity} entities.
 */
public class RoleRepositoryImpl implements RoleRepository {

    private final Logger log = LoggerFactory.getLogger(RoleRepositoryImpl.class);
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<RoleEntity> getRoles(EntityManager entityManager) {
        log.debug("Return all roles");
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<RoleEntity> cq = cb.createQuery(RoleEntity.class);
        
        // select from role
        Root<RoleEntity> role = cq.from(RoleEntity.class);
        cq.select(role);
        cq.orderBy(cb.asc(role.get(RoleEntity_.name)));
        
        // execute query
        TypedQuery<RoleEntity> tq = entityManager.createQuery(cq);
        List<RoleEntity> roles = tq.getResultList();
        
        return roles;
    }

}
