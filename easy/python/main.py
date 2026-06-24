import sys
from func_call import FuncCall

if __name__ == "__main__":
    cmd = sys.argv[1] if len(sys.argv) > 1 else "calc"
    # FuncCall.run_by_child(cmd)
    FuncCall.run_by_interface(cmd)
