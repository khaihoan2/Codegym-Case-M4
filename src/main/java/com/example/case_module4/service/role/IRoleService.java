package com.example.case_module4.service.role;

import com.example.case_module4.model.Role;
import com.example.case_module4.service.IGeneralService;

public interface IRoleService extends IGeneralService<Role> {
    Role findByName(String keyword);
}
