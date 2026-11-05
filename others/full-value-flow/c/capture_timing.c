struct LocalCaptureCell { const char *value; };
typedef const char *(*LocalCaptureRead)(struct LocalCaptureCell *);
// local_capture_read consumes the explicit context accompanying a C callback.
const char *local_capture_read(struct LocalCaptureCell *cell) { return cell->value; }
// callback_capture_local observes a context write after the function pointer is selected.
const char *callback_capture_local(const char *callback_capture_localInput) { struct LocalCaptureCell cell = {"safe"}; LocalCaptureRead selected = local_capture_read; cell.value = callback_capture_localInput; const char *callback_capture_localOut = selected(&cell); return callback_capture_localOut; }
// callback_capture_local_isolation keeps separate callback contexts apart.
const char *callback_capture_local_isolation(const char *callback_capture_local_isolationInput) { struct LocalCaptureCell first = {callback_capture_local_isolationInput}; struct LocalCaptureCell second = {"safe"}; LocalCaptureRead selected = local_capture_read; selected(&first); const char *callback_capture_local_isolationOut = selected(&second); return callback_capture_local_isolationOut; }
