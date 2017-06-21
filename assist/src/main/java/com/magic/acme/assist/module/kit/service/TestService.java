package com.magic.acme.assist.module.kit.service;

import com.magic.acme.assist.jpa.kit.entity.Log;
import java.util.List;

/**
 * Created by bert on 17-6-21.
 */
public interface TestService {

    List<Log> selectLogList();
}
