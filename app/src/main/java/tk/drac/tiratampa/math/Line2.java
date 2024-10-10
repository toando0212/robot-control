package tk.drac.tiratampa.math;

public class Line2 {
    public Vec2 a;
    public Vec2 b;

    public Line2(Vec2 a, Vec2 b) {
        this.a = a;
        this.b = b;
    }

    public Line2(double x0, double y0, double x1, double y1) {
        this.a = new Vec2(x0, y0);
        this.b = new Vec2(x1, y1);
    }

    public double theta() {
        if(deltaY() == 0)
            return Math.signum(deltaX()) * Math.PI / 2;

        return Math.atan(deltaX()/deltaY());
    }

    public double deltaX() {
        return this.b.x - this.a.x;
    }

    public double deltaY() {
        return this.b.y - this.a.y;
    }

    public double width() {
        return Math.abs(this.deltaX());
    }

    public double height() {
        return Math.abs(this.deltaY());
    }

    public double minX() {
        return a.x > b.x ? b.x : a.x;
    }

    public double maxX() {
        return a.x > b.x ? a.x : b.x;
    }

    public double minY() {
        return a.y > b.y ? b.y : a.y;
    }

    public double maxY() {
        return a.y > b.y ? a.y : b.y;
    }

    public double length2() {
        return Math.pow(this.width(), 2) + Math.pow(this.height(), 2);
    }

    public double length() {
        return Math.sqrt(this.length2());
    }
}
