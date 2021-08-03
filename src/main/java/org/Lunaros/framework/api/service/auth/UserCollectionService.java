package org.Lunaros.framework.api.service.auth;

import org.Lunaros.basemodel.HttpResponseTemp;
import org.Lunaros.framework.api.consolemodel.auth.CollectionMember;
import org.Lunaros.framework.api.consolemodel.auth.CollectionMembers;
import org.Lunaros.framework.api.model.collection.CollectionAuthorityMap;
import org.Lunaros.framework.api.model.collection.related.CollectionInfo;
import org.Lunaros.framework.api.model.collection.related.ResourceType;

import java.util.List;

/**
 * Created by KaiRen on 2016/9/22.
 */
public interface UserCollectionService {
    HttpResponseTemp<?> addUserToCollection(CollectionAuthorityMap authorityMap);

    HttpResponseTemp<?> deleteAllUserInCollection(int collectionId, ResourceType resourceType);

    HttpResponseTemp<?> addCollectionMember(int userId, CollectionMember member);

    HttpResponseTemp<?> modifyCollectionMember(int userId, CollectionMember member);

    HttpResponseTemp<?> addCollectionMembers(int userId, CollectionMembers members);

    HttpResponseTemp<?> deleteCollectionMember(int userId, CollectionAuthorityMap authorityMap);

    HttpResponseTemp<?> listCollectionMember(int userId, int collectionId, ResourceType resourceType);

    HttpResponseTemp<List<CollectionInfo>> listAllCollectionInfo(int userId, ResourceType collectionType);
}
