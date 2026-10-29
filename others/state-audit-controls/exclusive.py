import os

# WriteExclusive requires atomic exclusive creation and must retain the candidate signal.
def write_exclusive(directory, payload):
    descriptor = os.open(os.path.join(directory, "shared-output"), os.O_WRONLY | os.O_CREAT | os.O_EXCL, 0o600)
    try:
        os.write(descriptor, payload)
    finally:
        os.close(descriptor)
