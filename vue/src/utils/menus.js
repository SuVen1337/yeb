import {getRequest} from "@/utils/api";


export const initMenu = (router,store)=>{
    if(store.state.routes.length>0){
        return;
    }


    getRequest('/system/cfg/menu').then(data=>{
        if(data){
            //格式化Router
            let fmtRoutes = formatRoutes(data)
            //添加到Router
            router.addRoutes(fmtRoutes);
            //将数据存入vuex
            store.commit('initRoutes',fmtRoutes)
            //连接websocket
            // store.dispatch('connect')
        }
    })
}

export const formatRoutes=(routes)=>{
    let fmtRoutes = [];
    routes.forEach(router=>{
        let{
            path,
            component,
            name,
            iconCls,
            children,
        }=router;
        if(children && children instanceof Array){
            children=formatRoutes(children);
        }
        let fmRouter = {
            path:path,
            name:name,
            iconCls:iconCls,
            children:children,
            component(resolve){
                if(component.startsWith('Home')){
                    require(['../views/'+component+'.vue'],resolve);
                }else {
                let dir = component.substring(0,3).toLowerCase();
                require(['../views/'+dir+'/'+component+'.vue'],resolve);
                }

            }
        }
        fmtRoutes.push(fmRouter);
    })
    return fmtRoutes;
}