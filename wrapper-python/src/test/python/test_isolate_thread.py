from wrapper import add
from isolate_thread import IsolateThread


def test_isolate_thread():
    with IsolateThread() as isolate_thread:
        assert add(isolate_thread, 1, 2) == 3
        assert add(isolate_thread, 3, 4) == 7
