package tk.drac.tiratampa.math;

public class LinearTransform2 {
    protected LinearTransform2 prev;
    protected Line2 origin;
    protected Line2 target;

    public LinearTransform2(LinearTransform2 prev, Line2 origin, Line2 target) {
        this.prev = prev;
        this.origin = origin;
        this.target = target;
    }

    public LinearTransform2(Line2 origin, Line2 target) {
        this(null, origin, target);
    }

    public Vec2 transform(Vec2 vector) {
        Vec2 tmp = new Vec2(vector.x, vector.y);
        if(this.prev != null)
            tmp = this.prev.transform(vector);


        /* Translate to the origin */
        tmp.x -= origin.a.x;
        tmp.y -= origin.a.y;

        /* Rotate and align the vectors */
        double rotation = target.theta() - origin.theta();
        tmp = new Vec2(
                tmp.x * Math.cos(rotation) - tmp.y * Math.sin(rotation),
                tmp.y * Math.cos(rotation) + tmp.x * Math.sin(rotation)
        );

        /* Scale one down to the other */
        tmp.x *= target.width()  / origin.width();
        tmp.y *= target.height() / origin.height();

        /* Translate back */
        tmp.x += target.a.x;
        tmp.y += target.a.y;

        return tmp;
    }

    public Line2 transform(Line2 line) {
        return new Line2(
                this.transform(line.a),
                this.transform(line.b)
        );
    }
}
