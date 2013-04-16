
/*
* ��Ϣ�б����֧࣬��ͨ���Ӵ�����ʾ����ϸ���б���Ϣ
* 
* @param grid 	InfoGridLayout�����Grid
* @param config	InfoGridLayout������:
				grid		:	������
				layout		:	���ֶ������Ϊ�գ���ôĬ�Ϲ���layout
				paging 		: 	true/false �Ƿ��ҳ��Ĭ��Ϊfalse
				pageSize 	: 	��ҳ��С��Ĭ��Ϊ20
				displayMsg	: 	��ҳ��ʾ��Ϣ
				emptyMsg	: 	��ҳ��������Ϣ
				
* @see Ext.grid.Grid
*/
InfoGridLayout = function (config) {
    /**
	* ������
	* @see Ext.grid.Grid
	*/
    this.grid = config.grid;
    
    /**
	* layout
	*/
    this.layout = config.layout;
    
    /**
	* InfoGridLayout���ö���
	*/
    this.config = config;
	
	/**
	* �б�����
	*/
    this.toolBar = null;
    
	/**
	* ��ϸ��Ϣ����Ĭ��title
	*/
	this.defaultDetailTitle='More Infomation';	
	
	//��ʼ��
	this.init = function (config){
		if(this.layout==null){
			this.layout = new Ext.BorderLayout(document.body, {
										center: {
											resizeTabs: true,
											autoScroll: true										
										}
									});
		}

	};
	
	//չ�ֲ���
    this.render = function () {
    	this.layout.beginUpdate();
    	
    	this.layout.add('center', new Ext.GridPanel(this.grid));
    	var ds=this.grid.getDataSource();
        this.grid.render();
        
        if(ds!==null){
        	ds.load({params:{start:0, limit:20}});
        }
        //��ʾ��ҳ��
        var paging=config.paging;
		
		if(paging){
			this.setToolBar(null,paging);				
		}
		
        this.grid.getSelectionModel().selectFirstRow();
        this.layout.endUpdate();
    };
	
	//����ToolBar
    this.getToolBar = function (isCreate) {
    	if(this.toolBar==null && isCreate){
    		this.setToolBar();
    	}
    	return this.toolBar;
    	
    };
	
	/**
	* ������ϸ��Ϣ����
	* @param el Ext.ContentPanel��container
	* @param config ͬExt.ContentPanel��config,���bindColumn���ԣ�������ϸ���ڰ�������ֶ�
	*/
    this.addDetailPanel = function (el,config) {
    	this.layout.addRegion('south',{
										title: this.defaultDetailTitle,
										split:true,
										initialSize: 200,
										titlebar: true,
										collapsible: true,
										autoScroll: true,
										animate: true,
										minSize: 100,
										maxSize: 400,
										resizeTabs: true
									});
    
    	var panel= new Ext.ContentPanel(el, config);
    	this.layout.add('south',panel);
    	if(config.bindColumn){
    		this.bindDetailInfo(panel,config.bindColumn);
    	}
    	
    };
    
    this.bindDetailInfo = function (panel,mapping){
    	this.grid.on("rowclick",function(grid,rowIndex,e){
							var stoge=grid.getDataSource();
							var record=stoge.getAt(rowIndex);
							panel.setContent(record.get(mapping));
							
						});
    };
    
    //���ù�����
    this.setToolBar = function(container,paging) {
    	if(container==null){
    		container = this.grid.getView().getFooterPanel(true);
    	}
    	if(paging){
    		var ds=this.grid.getDataSource();
    		this.toolBar = new Ext.PagingToolbar(container, ds, {
							pageSize: config.pageSize==null?20:config.pageSize,
							displayInfo: true,
							displayMsg: config.displayMsg,
							emptyMsg: config.emptyMsg
						});
    		
    	}else{
    		this.toolBar=new Ext.Toolbar(container);
		}
    };
    
    
	this.init(config);
};

