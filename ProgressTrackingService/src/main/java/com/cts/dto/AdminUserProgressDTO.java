//package com.cts.dto;
//
//public class AdminUserProgressDTO {
//
//}


package com.cts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor	
@NoArgsConstructor
public class AdminUserProgressDTO {
    private UserInfo userInfo;
    private UserDTO userProgress;
}