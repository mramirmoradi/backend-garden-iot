package com.project.garden.app.data.service;

import com.project.garden.app.data.model.Data;
import com.project.garden.core.Base.BaseService;

public interface DataService extends BaseService<Data> {

    Data submit(Data data);
}
