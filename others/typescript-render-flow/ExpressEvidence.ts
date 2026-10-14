const app = express();
// app.get('/comment', handler);
const example = "app.get('/string', handler)";
function fake(app: any) { app.get('/shadow', handler); }
app.get('/real', handler);
function dynamic(id: string) { app.get(`/users/${id}`, handler); }
