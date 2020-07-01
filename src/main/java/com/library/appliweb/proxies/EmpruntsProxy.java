package com.library.appliweb.proxies;


import com.library.appliweb.beans.EmpruntBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "zuul-server")
@RibbonClient(name = "emprunts")
public interface EmpruntsProxy {

    @GetMapping(value = "/emprunts/emprunts/")
    Resource<EmpruntBean> getEmprunt(@PathVariable("id") int id);

    @RequestMapping(method = RequestMethod.GET, value = "/emprunts/emprunts/search/findByExemplaireBarcode?exemplaireBarcode={exemplaireBarcode}")
    PagedResources<EmpruntBean> findByExemplaireBarcode(@PathVariable("exemplaireBarcode") int barcode);
}
