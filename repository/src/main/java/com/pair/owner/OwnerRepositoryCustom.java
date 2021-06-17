package com.pair.owner;

import java.util.List;

public interface OwnerRepositoryCustom {

    List<Owner> findOwner(String id, String name, String email);
}
