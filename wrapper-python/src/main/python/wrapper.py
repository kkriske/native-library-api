from ctypes import c_int, POINTER, c_void_p
from loader import lib, function_factory

class GraalCreateIsolateParamsP(c_void_p):
    pass

class GraalIsolateP(c_void_p):
    pass

class GraalIsolateThreadP(c_void_p):
    pass

graal_create_isolate = function_factory(
    lib.graal_create_isolate,
    [GraalCreateIsolateParamsP, POINTER(GraalIsolateP), POINTER(GraalIsolateThreadP)],
    c_int
)

graal_tear_down_isolate = function_factory(
    lib.graal_tear_down_isolate,
    [GraalIsolateThreadP],
    c_int
)

add = function_factory(
    lib.add,
    [GraalIsolateThreadP, c_int, c_int],
    c_int
)
