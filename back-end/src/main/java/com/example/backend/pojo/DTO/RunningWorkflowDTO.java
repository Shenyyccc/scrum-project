package com.example.backend.pojo.DTO;

import com.example.backend.pojo.Runningworkflow;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RunningWorkflowDTO extends Runningworkflow {
    String progress;
}
