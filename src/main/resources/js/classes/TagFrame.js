/*
* Tag�����࣬��һ�����������ڣ���̬�������µ�tag�͹ر�tag,
* ÿ��tag����һ��iframe����������ʾ��ͬ��ҳ��
* 
* @param region: ��������
* @see Ext.LayoutRegion
*/
var TagFrame = function (region) {

this.wins = [];   //�Ѿ��򿪵Ĵ���
this.maxWins = 8;

/**
* �򿪴��ڣ���������Ѿ������򼤻�
*/
this.openWin=function (url, name) {
		var win=this.container(url, name);
		
        if(win!==null){
        	var panel=win.panel;
        	if(panel.getEl()){
        		this.activateWin(win);
        		return;
        	}else{
        		this.removeWin(win);
        	}
        	
        }
        
        this.addWin(url, name);
        
     };
/**
* ��������
*/    
this.addWin=function (url, name) {
		if(this.wins.length>=this.maxWins){
			
			this.removeWin(this.wins[0]);
		}

        var id = Ext.id();
        var iframe = document.createElement("iframe");
        iframe.id = id;
        iframe.src = url;
        
        iframe.setAttribute("frameborder", "no");
        iframe.setAttribute("scrolling", "auto");
        
        document.body.appendChild(iframe);
        
        
        var panel=new Ext.ContentPanel(id, {title:name, fitToFrame:true, closable:true});
        region.add(panel);
    	
    	this.wins[this.wins.length]={id:id,url:url,name:name,panel:panel};
    };
/**
* �����
*/
this.activateWin = function (win) {
        var panel=win.panel;
        if(panel.getEl()){
        	region.showPanel(panel.getId());
        }
        
    };
    
/**
* �Ƴ�����
*/    
this.removeWin =function (win)        //remove window
{
		
		if(win == null)return;
		var temparr = [];

		for(var i=0;i<this.wins.length;i++)
		{
			if(this.wins[i] != win){
				temparr[temparr.length] = this.wins[i];
			}
		}
		this.wins = temparr;
		
		var panel=win.panel;
		if(panel.getEl()){
			region.remove(panel,true);
		}
	};
	    
this.container = function (url,name){
		for(var i=0;i<this.wins.length;i+=1)
		{
			if(this.wins[i].name == name && this.wins[i].url == url)
			{
				return this.wins[i];
			}
		}
		return null;
	};
    
};

