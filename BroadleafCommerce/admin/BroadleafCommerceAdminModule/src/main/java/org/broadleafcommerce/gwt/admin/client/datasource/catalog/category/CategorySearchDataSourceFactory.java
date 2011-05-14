package org.broadleafcommerce.gwt.admin.client.datasource.catalog.category;

import org.broadleafcommerce.gwt.admin.client.datasource.CeilingEntities;
import org.broadleafcommerce.gwt.admin.client.datasource.EntityImplementations;
import org.broadleafcommerce.gwt.client.datasource.DataSourceFactory;
import org.broadleafcommerce.gwt.client.datasource.dynamic.ListGridDataSource;
import org.broadleafcommerce.gwt.client.datasource.dynamic.module.BasicEntityModule;
import org.broadleafcommerce.gwt.client.datasource.dynamic.module.DataSourceModule;
import org.broadleafcommerce.gwt.client.datasource.dynamic.module.JoinStructureModule;
import org.broadleafcommerce.gwt.client.datasource.relations.ForeignKey;
import org.broadleafcommerce.gwt.client.datasource.relations.JoinStructure;
import org.broadleafcommerce.gwt.client.datasource.relations.PersistencePerspective;
import org.broadleafcommerce.gwt.client.datasource.relations.PersistencePerspectiveItemType;
import org.broadleafcommerce.gwt.client.datasource.relations.operations.OperationTypes;
import org.broadleafcommerce.gwt.client.service.AppServices;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DataSource;

public class CategorySearchDataSourceFactory implements DataSourceFactory {

	public static final String symbolName = "allParentCategories";
	public static final String linkedObjectPath = "categoryXrefPK.category";
	public static final String linkedIdProperty = "id";
	public static final String targetObjectPath = "categoryXrefPK.subCategory";
	public static final String targetIdProperty = "id";
	public static final String sortField = "displayOrder";
	public static ListGridDataSource dataSource = null;
	
	/*public void createDataSource(String name, AsyncCallback<DataSource> cb) {
		OperationTypes operationTypes = new OperationTypes(OperationType.ENTITY, OperationType.ENTITY, OperationType.JOINSTRUCTURE, OperationType.ENTITY, OperationType.ENTITY);
		createDataSource(name, operationTypes, cb);
	}
	
	public void createDataSource(String name, OperationTypes operationTypes, AsyncCallback<DataSource> cb) {
		
	}*/

	public void createDataSource(String name, OperationTypes operationTypes, Object[] additionalItems, AsyncCallback<DataSource> cb) {
		if (dataSource == null) {
			PersistencePerspective persistencePerspective = new PersistencePerspective(operationTypes, new String[]{}, new ForeignKey[]{new ForeignKey(CategoryTreeDataSourceFactory.defaultParentCategoryForeignKey, EntityImplementations.CATEGORY, null)});
			persistencePerspective.addPersistencePerspectiveItem(PersistencePerspectiveItemType.JOINSTRUCTURE, new JoinStructure(symbolName, linkedObjectPath, linkedIdProperty, targetObjectPath, targetIdProperty, EntityImplementations.CATEGORY_XREF, sortField, true));
			DataSourceModule[] modules = new DataSourceModule[]{
				new BasicEntityModule(CeilingEntities.CATEGORY, persistencePerspective, AppServices.DYNAMIC_ENTITY),
				new JoinStructureModule(CeilingEntities.CATEGORY, persistencePerspective, AppServices.DYNAMIC_ENTITY)
			};
			dataSource = new ListGridDataSource(name, persistencePerspective, AppServices.DYNAMIC_ENTITY, modules);
			dataSource.buildFields(null, false, cb);
		} else {
			if (cb != null) {
				cb.onSuccess(dataSource);
			}
		}
	}

}
