package org.Lunaros.framework.api.controller.loadBalancer;

import java.util.List;

import org.Lunaros.basemodel.HttpResponseTemp;
import org.Lunaros.basemodel.ResultStat;
import org.Lunaros.framework.api.consolemodel.deployment.VersionInfo;
import org.Lunaros.framework.api.consolemodel.loadBalancer.NginxVersionDraft;
import org.Lunaros.framework.api.controller.ApiController;
import org.Lunaros.framework.api.service.loadBalancer.LoadBalancerVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jackfan on 17/3/6.
 */
@Controller
@RequestMapping("/api/loadBalancer/version")
public class LoadBalancerVersionController extends ApiController {
    @Autowired
    LoadBalancerVersionService versionService;

    @ResponseBody
    @RequestMapping(value = "/{lbId}", method = RequestMethod.POST)
    public HttpResponseTemp<?> createVersion(@RequestBody NginxVersionDraft version,
                                             @PathVariable int lbId) throws Exception {
        return ResultStat.OK.wrap(versionService.createVersion(version, lbId));
    }

    @ResponseBody
    @RequestMapping(value = "/id/{lbId}/{versionId}", method = RequestMethod.GET)
    public HttpResponseTemp<?> getVersion(@PathVariable int lbId,
                                          @PathVariable int versionId) throws Exception {
        return ResultStat.OK.wrap(versionService.getVersionByLbIdAndVersionId(lbId, versionId));
    }
    
    @ResponseBody
    @RequestMapping(value = "/list/{lbId}", method = RequestMethod.GET)
    public HttpResponseTemp<List<VersionInfo>> listVersion(@PathVariable int lbId) throws Exception {
        return ResultStat.OK.wrap(versionService.listVersionByLbId(lbId));
    }
}