export function requireFields(fields) {
  return (req, res, next) => {
    const missing = fields.filter((field) => !req.body?.[field]);
    if (missing.length) {
      res.status(400);
      return next(new Error(`Pflichtfelder fehlen: ${missing.join(', ')}`));
    }
    next();
  };
}
