package com.learntocodetogether.twentytwo;

import com.learntocodetogether.utils.Utils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class Day7 {
    static class Node {
        Node parent;
        Set<Node> children;
        String name;
        int size;

        public Node(Node parent, Set<Node> children, String name, int size) {
            this.parent = parent;
            this.children = children;
            this.name = name;
            this.size = size;
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Node)) return false;
            Node obj = (Node) object;
            return obj.name.equals(this.name);
        }
    }

    static Node getNode(String[] lines) {
        Pattern digits = Pattern.compile("\\d+");
        Node root = new Node(null, new HashSet<>(), "/", 0);
        Node cur = root;
        for(int i = 0; i < lines.length; i++) {
            String[] arr = lines[i].split("\\s+");
            if (arr[0].equals("$")) {
                if (arr[1].equals("cd")) {
                    if (arr[2].equals("..")) {
                        cur = cur.parent;
                    } else {
                        String dirName = arr[2];
                        Set<Node> children = cur.children;
                        if (children == null) {
                            children = new HashSet<>();
                        }
                        boolean found = false;
                        for(final var child : children) {
                            if (child.name.equals(dirName)) {
                                found = true;
                                cur = child;
                            }
                        }
                        if (!found) {
                            Node child = new Node(cur, new HashSet<>(), dirName, 0);
                            cur.children.add(child);
                            cur = child;
                        }
                    }
                } else if (arr[1].equals("ls")) {
                    for(int j = i + 1; j < lines.length && !lines[j].contains("$"); j++) {
                        String f = lines[j];
                        if (digits.matcher(f).find()) {
                            int size = Integer.parseInt(f.split("\\s+")[0]);
                            cur.size += size;
                        } else {
                            String dirName = arr[1];
                            if (cur.children == null) {
                                cur.children = new HashSet<>();
                            }
                            cur.children.add(new Node(cur, new HashSet<>(), dirName, 0));
                        }
                    }
                }
            }
        }
        return root;
    }
    public static int solvePart1(String input) {
        String[] lines = input.split("\n");
        Node root = getNode(lines);
        for(final var child : root.children) {
            System.out.println("Child: " + child.name);
        }
        return getSize(root, 0);
    }

    static int getSize(Node node, int size) {
        if (node == null || node.children == null || node.children.isEmpty()) return size;
        if (node.size <= 100_000) {
            size += node.size;
        }
        for(final var child : node.children) {
            getSize(child, size);
        }
        return size;
    }
    public static void main(String[] args) {
        String input = Utils.get("./input/day7");
        System.out.println(solvePart1(input));
    }
}