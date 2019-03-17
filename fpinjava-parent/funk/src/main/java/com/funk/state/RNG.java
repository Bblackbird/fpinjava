package com.funk.state;

import com.funk.common.Tuple;


public interface RNG {

  Tuple<Integer, RNG> nextInt();
}
