from ctypes import byref

from wrapper import graal_create_isolate, graal_tear_down_isolate, GraalIsolateThreadP


class IsolateThread:
    def __enter__(self):
        self.isolate_thread_p = GraalIsolateThreadP()
        graal_create_isolate(None, None, byref(self.isolate_thread_p))
        return self.isolate_thread_p

    def __exit__(self, exc_type, exc_val, exc_tb):
        graal_tear_down_isolate(self.isolate_thread_p)
