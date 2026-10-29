import os

# WriteShared uses a predictable entry in a caller-declared shared directory.
def write_shared(directory, payload):
    descriptor = os.open(os.path.join(directory, "shared-output"), os.O_WRONLY | os.O_CREAT, 0o600)
    try:
        os.write(descriptor, payload)
    finally:
        os.close(descriptor)
