from ctypes import byref
from wrapper import add, graal_create_isolate, graal_tear_down_isolate, GraalIsolateThreadP


def test_add():
    isolate_thread_p = GraalIsolateThreadP()
    graal_create_isolate(None, None, byref(isolate_thread_p))
    assert add(isolate_thread_p, 1, 2) == 3
    assert add(isolate_thread_p, 3, 4) == 7
    graal_tear_down_isolate(isolate_thread_p)
